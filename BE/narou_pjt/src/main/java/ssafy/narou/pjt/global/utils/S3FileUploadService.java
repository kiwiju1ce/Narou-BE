package ssafy.narou.pjt.global.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssafy.narou.pjt.global.error.ImageUploadException;
import ssafy.narou.pjt.global.properties.S3PathProperties;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private final AmazonS3Client amazonS3Client;
    private final S3PathProperties s3PathProperties;

    public String saveImageFile(MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        String s3FilePath = UUID.randomUUID().toString().substring(0,9) + fileName;

        long size = multipartFile.getSize();

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(multipartFile.getContentType());
        objectMetaData.setContentLength(size);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    s3PathProperties.getBucket(), s3FilePath, multipartFile.getInputStream(), objectMetaData
            );

            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3Client.putObject(putObjectRequest);
        }catch (Exception e){
            throw new ImageUploadException();
        }

        return s3PathProperties.getBaseUrl() + s3FilePath;
    }
}

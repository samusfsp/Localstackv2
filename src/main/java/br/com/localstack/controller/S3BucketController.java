package br.com.localstack.controller;
import br.com.localstack.dto.BucketDTO;
import br.com.localstack.service.S3BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/s3")
public class S3BucketController {

    private final S3BucketService s3BucketService;

    @Autowired
    public S3BucketController(S3BucketService s3BucketService) {
        this.s3BucketService = s3BucketService;
    }

    @PostMapping("/create-bucket/{bucketName}")
    public ResponseEntity<String> createBucket(@PathVariable String bucketName) {
        s3BucketService.createBucket(bucketName);
        return ResponseEntity.ok("Bucket criado com sucesso: " + bucketName);
    }

    @GetMapping("/list-buckets")
    public ResponseEntity<List<BucketDTO>> listBuckets() {
        List<Bucket> buckets = s3BucketService.listBuckets();
        List<BucketDTO> bucketDTOs = buckets.stream()
                .map(bucket -> new BucketDTO(bucket.name()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bucketDTOs);
    }

//    @PostMapping("/upload-object")
//    public ResponseEntity<String> uploadObject(
//            @RequestParam String bucketName,
//            @RequestParam String key,
//            @RequestParam String content) {
//        s3BucketService.uploadObject(bucketName, key, content);
//        return ResponseEntity.ok("Objeto enviado com sucesso para o bucket: " + bucketName);
//    }


    @GetMapping("/list-objects/{bucketName}")
    public ResponseEntity<List<S3Object>> listObjects(@PathVariable String bucketName) {
        List<S3Object> objects = s3BucketService.listObjects(bucketName);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/get-object/{bucketName}/{key}")
    public ResponseEntity<String> getObject(
            @PathVariable String bucketName,
            @PathVariable String key) {
        String objectContent = s3BucketService.getObject(bucketName, key);
        if (objectContent != null) {
            return ResponseEntity.ok(objectContent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-object/{bucketName}/{key}")
    public ResponseEntity<String> deleteObject(
            @PathVariable String bucketName,
            @PathVariable String key) {
        s3BucketService.deleteObject(bucketName, key);
        return ResponseEntity.ok("Objeto excluído com sucesso do bucket: " + bucketName);
    }
}

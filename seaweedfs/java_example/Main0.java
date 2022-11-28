package com.ttcntt.internal;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.File;
import java.net.URI;

public class Main0 {
    public static void main(String[] args) {
//        final AwsCredentials credentials = new AwsCredentials() {
//            @Override
//            public String accessKeyId() {
//                return "ttcnttKey";
//            }
//
//            @Override
//            public String secretAccessKey() {
//                return "ttcnttSecret";
//            }
//        };
        final AwsCredentials credentials = AwsBasicCredentials.create("ttcnttKey", "ttcnttSecret");
        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:8333"))
                .credentialsProvider(new AwsCredentialsProvider() {
                    @Override
                    public AwsCredentials resolveCredentials() {
                        return credentials;
                    }

                })
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(Boolean.TRUE).build())
                .build();

        System.out.println(s3Client.listBuckets().buckets().size());

//        createBucket(s3Client, "mvn");
        uploadObject(s3Client, "mvn1", "settings.xml", "E:\\mvn\\settings.xml");
        System.out.println(s3Client.listBuckets().buckets().size());
    }

    public static void createBucket(S3Client s3Client, String bucketName) {

        try {
            S3Waiter s3Waiter = s3Client.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.createBucket(bucketRequest);
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            // Wait until the bucket is created and print out the response.
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(bucketName + " is ready");

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void uploadObject(S3Client s3Client, String bucketName, String key, String path) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(objectRequest, RequestBody.fromFile(new File(path)));
    }
}

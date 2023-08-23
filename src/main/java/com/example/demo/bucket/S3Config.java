package com.example.demo.bucket;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Config {
	@Value("${aws.region}")
	private String awsRegion;

	@Value("${aws.s3.mock}")
	private boolean mock;

	AwsCredentials credentials = new
			AwsCredentials() {
				@Override
				public String accessKeyId() {
					return "AKIAVKCSIJZKFC56IOVC";
				}

				@Override
				public String secretAccessKey() {
					return "doexq3dkn036Gaf2Q7jQETGQe6cS7NYCO9kt6CAM";
				}
			};
	AwsCredentialsProvider credentialsProvider = new AwsCredentialsProvider() {
		@Override
		public AwsCredentials resolveCredentials() {
			return credentials;
		}
	};


	@Bean
	public S3Client s3Client() {
		if(mock){
			return new FakeS3();
		}
		return S3Client.builder().credentialsProvider(credentialsProvider).endpointOverride(URI.create("https://s3.amazonaws.com/bekscloud/info"))
				.region(Region.of(awsRegion))
				.build();
	}

}

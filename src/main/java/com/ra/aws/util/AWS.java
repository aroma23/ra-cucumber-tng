package com.ra.aws.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.List;

public class AWS {
    public static class S3 {
        public static AmazonS3 createClient(String accessKey, String secretKey) {
            return createClient(accessKey, secretKey, "us-east-1");
        }

        public static AmazonS3 createClient(String accessKey, String secretKey, String region) {
            AmazonS3 s3Client = null;
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            try {
                s3Client = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                        .withClientConfiguration(new ClientConfiguration().withMaxConnections(10)).withRegion(region)
                        .build();
            } catch (SdkClientException e) {
                e.printStackTrace();
            }
            return s3Client;
        }
    }

    public static class SQS {
        public static AmazonSQS createSqsClient(String accessKey, String secretKey) {
            return createSqsClient(accessKey, secretKey, "us-east-1");
        }

        public static AmazonSQS createSqsClient(String accessKey, String secretKey, String region) {
            AmazonSQS amazonSQS = null;
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            ClientConfiguration clientConfig = new ClientConfiguration().withMaxConnections(1800000);
            amazonSQS = AmazonSQSAsyncClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .withRegion(region)
                    .withClientConfiguration(clientConfig)
                    .build();
            return amazonSQS;
        }

        public static List<Message> getRequestsFromQueue(AmazonSQS amazonSQS, String queue){
            ReceiveMessageRequest receiveMsgRequest = new ReceiveMessageRequest()
                    .withQueueUrl(queue)
                    .withWaitTimeSeconds(20)
                    .withMaxNumberOfMessages(10);

            return amazonSQS.receiveMessage(receiveMsgRequest).getMessages();
        }

        public static Message getRequestFromQueue(AmazonSQS amazonSQS, String queue, String requestId){
            List<Message> messages = getRequestsFromQueue(amazonSQS, queue);
            Message returnMsg = null;
            for(Message message: messages) {
//                System.out.println(message.getBody());
                if (message.getBody().contains(requestId)) {
                    returnMsg = message;
                    break;
                }
            }
            return returnMsg;
        }

        public static void purgeQueue(AmazonSQS amazonSQS, String queue) {
            amazonSQS.purgeQueue(new PurgeQueueRequest(queue));
        }

        public static void deleteMessage(AmazonSQS amazonSQS, Message message, String queue){
            amazonSQS.deleteMessage(new DeleteMessageRequest(queue, message.getReceiptHandle()));
        }

        public static String postRequestToQueue(AmazonSQS amazonSQS, String queue, String messageBody) {
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queue)
                    .withMessageBody(messageBody);

            return amazonSQS.sendMessage(sendMessageRequest).getMessageId();
        }
    }
}

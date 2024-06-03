import boto3
import logging
import json

from dlqhandler.event.message import Message

logger = logging.getLogger()
logger.setLevel(logging.INFO)

class SQSQueue:
    def __init__(self, queue_url, region_name='us-east-1'):
        self.queue_url = queue_url
        self.session = boto3.Session()
        self.sqs_client = boto3.client("sqs", region_name=region_name)

    def receive_messages_dlq(self, event=None, max_number=10, wait_time=0):
        try:
            logger.info('Starting to read messages from the queue')
            
            messages = self.sqs_client.receive_message(
                QueueUrl=self.queue_url,
                AttributeNames=['All'],
                MaxNumberOfMessages=max_number,
                WaitTimeSeconds=wait_time
            )

            logger.info('Received messages: %s', messages)
            if 'Messages' not in messages:
                logger.info('No messages to retrieve from SQS: Empty content')
                return []
            result = []  
            for msg in messages['Messages']:
                body = json.loads(msg['Body'])
                message_obj = Message(
                    service=body['Service'],
                    event=body['Event'],
                    time=body['Time'],
                    bucket=body['Bucket'],
                    request_id=body['RequestId'],
                    host_id=body['HostId']
                )
                result.append(message_obj.to_dict(), msg['ReceiptHandle'])

            return result
        except Exception as e:
            logger.exception("Error receiving messages: %s", e)
            return []

    def delete_message_dlq(self, receipt_handle):
        try:
            self.sqs_client.delete_message(
                QueueUrl=self.queue_url,
                ReceiptHandle=receipt_handle
            )
            logger.info(f"Message with receipt handle {receipt_handle} deleted successfully.")
        except Exception as e:
            logger.exception("Error deleting message: %s", e)
            raise e
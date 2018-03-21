import boto3
from boto3.dynamodb.conditions import Attr

class muse(object):

  table = boto3.resource('dynamodb', region_name='eu-west-1').Table("User")

  def addUser(self, first_name, last_name, user_mail, user_birthday, user_mobile):
    if self.table.scan(
        FilterExpression=Attr("info.mail").eq(user_mail)
      )['Items'] != []:
      return '305'
    return self.table.put_item(
      Item={
        'id': len(self.getUser()),
        'info': {
          'first_name': first_name.capitalize(),
          'last_name': last_name.capitalize(),
          'premobile': '+33',
          'mobile': user_mobile,
          'mail': user_mail,
          'country': 'fr',
          'birthday': user_birthday,
        },
        'profile': {
        },
        'type': 'user',
        'logs': {
          'log_id': user_mail,
          'log_pwd': 'dev',
        },
        'favorites': {
          'users': [
          ],
          'events': [
          ]
        },
        'bonus': {
        }
      }
    )['ResponseMetadata']['HTTPStatusCode']

  def getUser(self, user_id=-1):
    if user_id == -1:
      return self.table.scan()['Items']
    else:
      return self.table.get_item(
        Key={
          'id': user_id
        }
      )['Item']

  def updateUser(self, user_id, value_key, value_new):
    return self.table.update_item(
      Key={
        'id': user_id
      },
      UpdateExpression = 'SET ' + value_key + ' = :key',
      ExpressionAttributeValues = {
        ':key': value_new,
      }
    )['ResponseMetadata']['HTTPStatusCode']

  def connexionUser(self, user_log_id, user_log_password):
    user = self.table.scan(
      FilterExpression=Attr("logs.log_id").eq(user_log_id)
    )['Items']
    if user != [] and user[0]['logs']['log_pwd'] == user_log_password:
      return user[0]
    return {}

class meve(object):

  table = boto3.resource('dynamodb', region_name='eu-west-1').Table("Event")

  def getEvent(self, event_id=-1):
    if event_id == -1:
      return self.table.scan()['Items']
    else:
      return self.table.get_item(
        Key={
          'id': event_id
        }
      )['Item']

import tornado.ioloop
import tornado.web

import simplejson as json

from muse import muse

class ConnexionHandler(tornado.web.RequestHandler):
    def get(self):
        user_id = self.get_query_argument("user", "")
        user_pwd = self.get_query_argument("pwd", "")
        if user_id != "" and user_pwd != "":
            self.write(json.dumps(muse().connexionUser(user_id, user_pwd), use_decimal=True))
        else:
            self.write("401")

class CreateHandler(tornado.web.RequestHandler):
    def get(self):
        first_name = self.get_query_argument("fn", "")
        last_name = self.get_query_argument("ln", "")
        user_mail = self.get_query_argument("uma", "")
        user_birthday = self.get_query_argument("ub", "")
        user_mobile = self.get_query_argument("umo", "617763092")
        if first_name != "" and last_name != "" and user_mail != "" and user_birthday != "":
            self.write(json.dumps(muse().addUser(first_name, last_name, user_mail, user_birthday, user_mobile)))
        else:
            self.write("400")


def make_app():
    return tornado.web.Application([
        (r"/user/connect", ConnexionHandler),
        (r"/user/create", CreateHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(8888)
    tornado.ioloop.IOLoop.current().start()

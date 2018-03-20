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
            self.write("404")


def make_app():
    return tornado.web.Application([
        (r"/connexion", ConnexionHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(8888)
    tornado.ioloop.IOLoop.current().start()

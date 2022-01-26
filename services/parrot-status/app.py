import enum

from flask import Flask, jsonify, render_template
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config.from_json('config.json')
db = SQLAlchemy(app)


class ArticleStatus(enum.Enum):
    DRAFT = 1
    PUBLISHER = 2
    TRASHED = 3


class Article(db.Model):
    __table_args__ = {'schema': 'parrot'}
    id = db.Column(db.Integer, primary_key=True)
    author_id = db.Column(db.Integer, nullable=False)
    title = db.Column(db.String, nullable=False)
    content = db.Column(db.Text)
    # status = db.Column(db.Enum, enum=ArticleStatus)


@app.route('/hello/<title>')
def hello_world(title):
    return render_template('index.html', title=title)


@app.route('/articles/<article_id>')
def article_detail(article_id):
    return jsonify({
        'status': 'uccess',
        'message': f'{article_id}'
    })


@app.route('/articles', methods=['POST'])
def create_article():
    db.session.add(Article(author_id=1, title="About hello world", content="= Hello world"))
    db.session.commit()
    return jsonify(Article.query.all())


@app.route('/trending')
def trending():
    return 'ojbk'


if __name__ == '__main__':
    app.run()

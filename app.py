from flask import Flask, jsonify, request #import objects from the Flask model
from flasgger import Swagger, LazyString, LazyJSONEncoder
from flasgger import swag_from

import pickle

import pandas as pd

import json

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity




#Memanggil fungsi yang ada di file proses.py

app = Flask(__name__) #define app using Flask
app.json_encoder = LazyJSONEncoder

swagger_template = dict(
    info = {
        'title': LazyString(lambda:'API Data Science Platinum Challenge'),
        'version': LazyString(lambda:'1.0.0'),
        'description': LazyString(lambda:'Dokumentasi Data Science Class Platinum Challenge')
        }, host = LazyString(lambda: request.host)
)

swagger_config = {
        "headers":[],
        "specs":[
            {
            "endpoint":'docs',
            "route":'/docs.json'
            }
        ],
        "static_url_path":"/flasgger_static",
        "swagger_ui":True,
        "specs_route":"/docs/"
}
swagger = Swagger(app, template=swagger_template, config=swagger_config)


df=pd.read_csv('data.csv')

#POST METHOD MODEL NEURAL NETWORK
@swag_from("docs/post_text_nn.yml", methods=['GET'])
@app.route('/post_text_nn/<string:text>', methods=['GET'])
def postTextNN(text):
    string = text

    with open('Model/vectorizer_model1.pkl', 'rb') as f: 
        vectorizer = pickle.load(f)

    with open('Model/tfidf_matrix1.pkl', 'rb') as g: 
        tfidf_matrix = pickle.load(g)

    def recommend_recipes(input_ingredients, n=5):
        # Preprocessing input pengguna
        input_ingredients = ' '.join(input_ingredients)

        # Menghitung kemiripan cosine antara input pengguna dengan resep-resep dalam dataset
        input_tfidf = vectorizer.transform([input_ingredients])
        similarities = cosine_similarity(input_tfidf, tfidf_matrix)

        # Mengambil indeks resep yang paling mirip
        similar_indices = similarities.argsort()[0][::-1][:n]

        # Mengembalikan rekomendasi resep
        recommended_recipes = df.iloc[similar_indices]['Title'].tolist()
        return recommended_recipes
    
    user_input = [string]  # Contoh input pengguna
    recommendations = recommend_recipes(user_input, n=5)

    recipe_list = []
    # Menampilkan rekomendasi resep
    print("Rekomendasi Resep:")
    for recipe in recommendations:
        recipe_list.append(recipe)

    recipe_json = json.dumps(recipe_list)

    return recipe_json


# @swag_from("docs/post_file_nn.yml", methods=['POST'])
# @app.route('/post_file_nn', methods=['POST'])
# def postFileNN():
#     file = request.files['file']
#     try:
#         data = pd.read_csv(file, encoding='iso-8859-1',error_bad_lines=False)
#     except:
#         data = pd.read_csv(file, encoding='utf-8',error_bad_lines=False) 
#     process_csv_nn(data)
#     return "DONE"
# #=======================================================================================#
# #POST METHOD MODEL LSTM
# @swag_from("docs/post_text_lstm.yml", methods=['POST'])
# @app.route('/post_text_lstm', methods=['POST'])
# def type():
#     string = str(request.form["text"])
#     string = cleansing(string)
#     pred_sentiment(string)

#     classes = pred_sentiment(string)
#     hasil = pred(classes)

#     query_tabel = "insert into prediksi_tweet (tweet,prediksi) values (?, ?)"
#     value = (string, hasil)
#     mycursor.execute(query_tabel, value)
#     db.commit()

#     return  f"Hasil adalah {hasil}"

# @swag_from("docs/post_file_lstm.yml", methods=['POST'])
# @app.route('/post_file_lstm', methods=['POST'])
# def postFileLSTM():
#     file = request.files['file']
#     try:
#         data = pd.read_csv(file, encoding='iso-8859-1', error_bad_lines=False)
#     except:
#         data = pd.read_csv(file, encoding='utf-8', error_bad_lines=False) 
#     process_csv_lstm(data)
#     return "DONE"


# if __name__ == '__main__':
#     app.run(host='0.0.0.0')
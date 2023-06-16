from flask import Flask, jsonify
from flasgger import Swagger
from sklearn.metrics.pairwise import linear_kernel
import joblib
import pandas as pd
import os

app = Flask(__name__)
swagger_template = {
    'info':{
        'title': 'API Reciphy',
        'description': 'Test',
        'versions' : '1.0.0'
    }
}
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
        "specs_route":"/swagger/"
}

# Swagger
swagger = Swagger(app, template=swagger_template, config=swagger_config)

# Datasets
df=pd.read_csv('datasets/data.csv')
@app.route('/')
def index():
    return {'ML-Team':'Reciphy'}

@app.route('/similar/<string:menu>', methods=['GET'])
def similar(menu):
    """Returning a list of similar menu
    ---
    parameters:
      - name: menu
        in: path
        type: string
        required: true
    responses:
        200:
            description: Successful response
        400:
            description: Bad Request
        500:
            description: Internal Server Error
    """
    vector = joblib.load('model/tfidf_vectorizer.pkl')
    matrix = joblib.load('model/tfidf_matrix.pkl')

    def get_similar_menu(input_menu, n=5):
        # Preprocessing input
        input_menu = ' '.join(input_menu)

        # Cosine Similarities in Input Menu with all Menu
        input_tfidf = vector.transform([input_menu])

        # Using Linear Kernel
        similarities = linear_kernel(input_tfidf, matrix)

        # Taking index
        similar_indices = similarities.argsort()[0][::-1][:n]

        # Return Menu
        similar_menu = df.iloc[similar_indices]['Title'].tolist()
        return similar_menu
    
    user_input = [menu]  
    similars = get_similar_menu(user_input, n=5)

    similars_menu_list = []
    
    res = similars
    res = list(dict.fromkeys(res))

    for i in res:
        if i.lower() == ' '.join(user_input).lower():
            pass
        else:
            similars_menu_list.append(i)
    
    # result = {
    #     'similars':similars_menu_list
    # }
    return jsonify(similars_menu_list)
    # return jsonify(result)

if __name__ == "__main__":
    app.run(debug=True, port=os.getenv("PORT", default=5000))

const mysql = require('mysql')

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    database: 'reciphy',
    password: ''
})

module.exports = connection;
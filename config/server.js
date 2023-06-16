const express = require('express')
const app = express()
const bodyParser = require('body-parser')

const recordRouter = require('../routes/route')

app.use(bodyParser.urlencoded({extended: true}))
app.use(recordRouter)

app.get("/", (req, res) => {
    console.log("Response success")
    res.send("Response Success!")
})

const PORT = process.env.PORT || 5000
app.listen(PORT, () => {
    console.log("Server is up and listening on " + PORT)
})
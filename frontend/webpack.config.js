const HtmlWebpackPlugin = require("html-webpack-plugin");
const HardSourceWebpackPlugin = require("hard-source-webpack-plugin");
const path = require("path")

module.exports = [{
    mode: "production",
    entry: "./src/index.tsx",
    target: "web",
    devtool: "source-map",
    performance: {
        hints: false
    },
    module: {
        rules: [{
            test: /\.ts(x?)$/,
            include: /src/,
            resolve: {
                extensions: [".ts", ".tsx", ".js"]
            },
            use: [{ loader: "ts-loader" }]
        }]
    },
    output: {
        path: path.resolve(__dirname, "../src/main/resources/public"),
        filename: "index.js"
    },
    plugins: [
        new HardSourceWebpackPlugin(),
        new HtmlWebpackPlugin({
            template: "./src/index.html"
        })
    ]
}]
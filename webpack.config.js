var path = require('path');

module.exports = {
    entry: './src/main/frontend/index.tsx',
    mode: 'production',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                    test: /\.tsx?$/,
                    use: 'ts-loader',
                    exclude: /node_modules/,
            },
            {
                    test: /\.s[ac]ss$/i,
                    use: ["style-loader", "css-loader", "sass-loader"],
            }
        ]
    },
    resolve: {
        alias: {
            frontend: path.resolve(__dirname, 'src/main/frontend/')
        },
        extensions: ['.tsx', '.ts', '.js']
    },
};
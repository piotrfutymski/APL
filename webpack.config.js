var path = require('path');

module.exports = {
    entry: './src/main/js/index.tsx',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'ts-loader'
                }]
            }
        ]
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js']
    }
};
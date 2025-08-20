/** @type {import('tailwindcss').Config} */
import PrimeUI from 'tailwindcss-primeui';

export default {
    darkMode: ['selector', '[class*="app-dark"]'],
    content: ['./index.html', './src/**/*.{html,js,ts}', './public/**/*.json'],
    plugins: [PrimeUI],
    theme: {
        screens: {
            sm: '576px',
            md: '768px',
            lg: '992px',
            xl: '1200px',
            '2xl': '1920px'
        },
        extend: {
            keyframes: {
                'spin-slow': {
                    '0%': { transform: 'translateX(-50%) rotate(0deg)' },
                    '100%': { transform: 'translateX(-50%) rotate(360deg)' }
                }
            },
            animation: {
                'spin-slow': 'spin-slow 20s linear infinite'
            },
            colors: {
                sitebg: '#0b0b2a',
                cardbg: '#1a2238'
            }
        }
    }
};

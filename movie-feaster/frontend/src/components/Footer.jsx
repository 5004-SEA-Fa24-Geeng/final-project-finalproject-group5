// Footer.jsx
import React from 'react';
import './Footer.css';

const Footer = () => {
    return (
        <footer className="app-footer">
            <div className="footer-container">
                <p>&copy; {new Date().getFullYear()} Movie Feast. All rights reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;
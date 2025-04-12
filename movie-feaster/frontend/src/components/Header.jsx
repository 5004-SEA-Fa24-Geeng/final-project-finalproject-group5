// Header.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

const Header = () => {
    return (
        <header className="app-header">
            <div className="header-container">
                <div className="logo">
                    <Link to="/">Movie Feast</Link>
                </div>
                <nav className="main-nav">
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/movies">Movies</Link></li>
                    </ul>
                </nav>
            </div>
        </header>
    );
};

export default Header;
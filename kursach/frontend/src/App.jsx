// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Layout } from 'antd';
import CatalogPage from './pages/CatalogPage';
import ClothDetailPage from './pages/ClothDetailPage';
import NotFoundPage from './pages/NotFoundPage';
import Header from './components/Header';
import Footer from './components/Footer';
import CartPage from './pages/CartPage';
import OrderCompletePage from './pages/OrderCompletePage'; // Импортируйте OrderCompletePage

const { Content } = Layout;

function App() {
    return (
        <Router>
            <Layout style={{ minHeight: '100vh' }}>
                <Header />
                <Content style={{ padding: '0 50px', paddingTop: 0 }}>
                    <div className="site-layout-content" style={{ padding: 24, background: '#fff', minHeight: 380 }}>
                        <Routes>
                            <Route path="/" element={<CatalogPage />} />
                            <Route path="/product/:id" element={<ClothDetailPage />} />
                            <Route path="/cart" element={<CartPage />} />
                            <Route path="/order-complete" element={<OrderCompletePage />} /> {/* Добавьте маршрут для OrderCompletePage */}
                            <Route path="*" element={<NotFoundPage />} />
                        </Routes>
                    </div>
                </Content>
                <Footer />
            </Layout>
        </Router>
    );
}

export default App;
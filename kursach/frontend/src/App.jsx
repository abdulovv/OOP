// src/App.jsx
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Layout } from 'antd';
import CatalogPage from './pages/CatalogPage';
import ClothDetailPage from './pages/ClothDetailPage';
import NotFoundPage from './pages/NotFoundPage';
import Header from './components/Header';
import Footer from './components/Footer';
import CartPage from './pages/CartPage';
import OrderCompletePage from './pages/OrderCompletePage';
import OrderSuccessPage from './pages/OrderSuccessPage';
import StatisticsPage from './pages/StatisticsPage';
import OrdersPage from "./pages/OrdersPage"; // Импортируем StatisticsPage

const { Content } = Layout;

function App() {
    const [cart, setCart] = useState(() => {
        const storedCart = localStorage.getItem('shoppingCart');
        return storedCart ? JSON.parse(storedCart) : [];
    });

    const updateCartInLocalStorage = (newCart) => {
        localStorage.setItem('shoppingCart', JSON.stringify(newCart));
    };

    const addToCart = (productToAdd, size, availableCount) => {
        const existingItem = cart.find(item => item.id === productToAdd.id && item.size === size);
        let newCart;
        if (existingItem) {
            newCart = cart.map(item =>
                item.id === productToAdd.id && item.size === size
                    ? { ...item, quantity: item.quantity + 1, availableCount }
                    : item
            );
        } else {
            newCart = [...cart, { ...productToAdd, size, quantity: 1, availableCount }];
        }
        setCart(newCart);
        updateCartInLocalStorage(newCart);
        console.log('Товар добавлен в корзину:', { ...productToAdd, size, quantity: 1, availableCount });
    };

    const removeItemFromCart = (productId, size) => {
        const newCart = cart.filter(item => !(item.id === productId && item.size === size));
        setCart(newCart);
        updateCartInLocalStorage(newCart);
    };

    const clearCart = () => {
        setCart([]);
        localStorage.removeItem('shoppingCart');
    };

    const updateItemQuantity = (itemId, size, quantity) => {
        const newCart = cart.map(item =>
            item.id === itemId && item.size === size
                ? { ...item, quantity: quantity }
                : item
        );
        setCart(newCart);
        updateCartInLocalStorage(newCart);
    };

    return (
        <Router>
            <Layout style={{ minHeight: '100vh' }}>
                <Header cartItems={cart} />
                <Content style={{ padding: '0 50px', paddingTop: 0 }}>
                    <div className="site-layout-content" style={{ padding: 24, background: '#fff', minHeight: 380 }}>
                        <Routes>
                            <Route path="/" element={<CatalogPage />} />
                            <Route
                                path="/product/:id"
                                element={<ClothDetailPage onAddToCart={addToCart} />}
                            />
                            <Route
                                path="/cart"
                                element={<CartPage
                                    cartItems={cart}
                                    onRemoveItem={removeItemFromCart}
                                    onClearCart={clearCart}
                                    onQuantityChange={updateItemQuantity}
                                />}
                            />
                            <Route path="/order-complete" element={<OrderCompletePage onClearCart={clearCart} />} />
                            <Route path="/order-success" element={<OrderSuccessPage />} />
                            <Route path="/statistics" element={<StatisticsPage />} /> {/* Новый маршрут для статистики */}
                            <Route path="/orders" element={<OrdersPage />} />
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
// src/components/Header.jsx
import React from 'react';
import { Layout, Menu, Input } from 'antd';
import { Link, useLocation } from 'react-router-dom';
import { ShoppingCartOutlined, ProfileOutlined } from '@ant-design/icons';

const { Header: AntdHeader } = Layout;
const { Search } = Input;

function Header() {
    const location = useLocation();
    const isCatalogPage = location.pathname === '/';

    return (
        <AntdHeader style={{ background: '#000', color: '#fff', width: '100%', zIndex: 10, display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '0 50px' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <Link to="/" style={{ color: '#fff', marginRight: 30, display: 'flex', alignItems: 'center' }}>

                    Главное
                </Link>
                <Link to="/statistics" style={{ color: '#fff', display: 'flex', alignItems: 'center' }}>

                    Статистика
                </Link>
            </div>


            {isCatalogPage && (
                <div style={{ width: '30%', display: 'flex', alignItems: 'center', justifyContent: 'center', height: '100%' }}>
                    <Search placeholder="Поиск товаров" onSearch={value => console.log(value)} style={{ width: '100%' }} />
                </div>
            )}
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <Link to="/cart" style={{ color: '#fff', marginRight: 30, display: 'flex', alignItems: 'center' }}>
                    <ShoppingCartOutlined style={{ fontSize: '20px', marginRight: 5 }} />
                    Корзина
                </Link>
                <Link to="/orders" style={{ color: '#fff', display: 'flex', alignItems: 'center' }}>
                    <ProfileOutlined style={{ fontSize: '20px', marginRight: 5 }} />
                    Заказы
                </Link>
            </div>
        </AntdHeader>
    );
}

export default Header;
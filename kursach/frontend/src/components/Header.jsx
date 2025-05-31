// src/components/Header.jsx
import React from 'react';
import { Layout, Typography } from 'antd';
import { Link } from 'react-router-dom';
import { ShoppingCartOutlined, ProfileOutlined, LineChartOutlined, SettingOutlined } from '@ant-design/icons';

const { Header: AntdHeader } = Layout;
const { Title } = Typography;

function Header() {
    return (
        <AntdHeader style={{ background: '#000', color: '#fff', width: '100%', zIndex: 10, display: 'flex', justifyContent: 'center', alignItems: 'center', padding: '0 50px' }}>
            <div style={{ position: 'absolute', left: '50px', display: 'flex', alignItems: 'center' }}>
                <Link to="/admin" style={{ color: '#fff', marginRight: 30, display: 'flex', alignItems: 'center' }}>
                    <SettingOutlined style={{ fontSize: '20px', marginRight: 5 }} />
                    Управление
                </Link>
            </div>
            <Link to="/" style={{ color: '#fff', margin: 0, fontFamily: 'cursive', fontSize: '1.5em' }}>
                KURSACH
            </Link>
            <div style={{ position: 'absolute', right: '50px', display: 'flex', alignItems: 'center' }}>
                <Link to="/cart" style={{ color: '#fff', marginRight: 30, display: 'flex', alignItems: 'center' }}>
                    <ShoppingCartOutlined style={{ fontSize: '20px', marginRight: 5 }} />
                    Корзина
                </Link>
                <Link to="/orders" style={{ color: '#fff', marginRight: 30, display: 'flex', alignItems: 'center' }}>
                    <ProfileOutlined style={{ fontSize: '20px', marginRight: 5 }} />
                    Заказы
                </Link>
                <Link to="/statistics" style={{ color: '#fff', display: 'flex', alignItems: 'center' }}>
                    <LineChartOutlined style={{ fontSize: '20px', marginRight: 5 }} />
                    Статистика
                </Link>
            </div>
        </AntdHeader>
    );
}

export default Header;
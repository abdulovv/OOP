// src/components/Footer.jsx
import React from 'react';
import { Layout } from 'antd';

const { Footer: AntdFooter } = Layout;

function Footer() {
    return (
        <AntdFooter style={{ textAlign: 'center', background: '#000', color: '#fff', width: '100%', padding: '24px 0' }}>
            Магазин одежды © 2025
        </AntdFooter>
    );
}

export default Footer;
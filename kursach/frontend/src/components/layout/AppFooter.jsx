import {Layout} from "antd";
import React from "react";

const footerStyle = {
    textAlign: 'center',
    color: '#fff',
    height: 'calc(7vh)',
    lineHeight: '6px',
    backgroundColor: '#0695f8',
    border: '1.2px solid #000'
};

export default function appFooter(){
    return <Layout.Footer style={footerStyle}>Footer</Layout.Footer>
}
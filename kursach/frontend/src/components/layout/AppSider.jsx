import {Layout} from "antd";

const siderStyle = {
    textAlign: 'center',
    height: 'calc(100vh)',
    lineHeight: 'calc(100vh)',
    color: '#fff',
    backgroundColor: '#171717',
    border: '1.2px solid #000'
};

export default function AppSider(){
    return <Layout.Sider style={siderStyle} width={"20%"}>Sider</Layout.Sider>
}
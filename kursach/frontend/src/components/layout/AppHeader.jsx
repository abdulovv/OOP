import {Layout} from "antd";

const headerStyle = {
    textAlign: 'center',
    color: '#fff',
    height: 'calc(7vh)',
    paddingInline: 48,
    lineHeight: 'calc(7vh)',
    backgroundColor: '#10a80a',
    border: '1.2px solid #000'
};

export default function AppHeader() {
    return <Layout.Header style={headerStyle}>Header</Layout.Header>
}
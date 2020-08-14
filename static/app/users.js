const hostView = {template: '<host-view></host-view>'}
const adminView = {template: '<admin-view></admin-view>'}

const router = new VueRouter({
    mode: 'hash',
    routes:[
        {path: '/host', component: hostView},
        {path: '/admin', component: adminView}
    ]
});

var app = new Vue({
    router,
    el: '#users'
    });
const demo = {
  categories: [
    {name:'Casa', color:'#2d6a59', value:'€ 734', percent:'34%'},
    {name:'Alimentari', color:'#e98972', value:'€ 472', percent:'22%'},
    {name:'Trasporti', color:'#e7ba58', value:'€ 368', percent:'17%'},
    {name:'Tempo libero', color:'#7191bb', value:'€ 302', percent:'14%'},
    {name:'Altro', color:'#b6c27b', value:'€ 280', percent:'13%'}
  ],
  transactions: [
    {icon:'▰', bg:'#e4f0eb', title:'Stipendio', category:'Entrate', date:'Oggi, 09:42', value:'+ € 3.250,00', incoming:true},
    {icon:'⌂', bg:'#f4e9db', title:'Affitto appartamento', category:'Casa', date:'Ieri, 08:15', value:'− € 850,00'},
    {icon:'◉', bg:'#f8e8e5', title:'Esselunga', category:'Alimentari', date:'28 lug, 18:32', value:'− € 76,40'},
    {icon:'◆', bg:'#e7edf5', title:'Spotify', category:'Abbonamenti', date:'27 lug, 07:10', value:'− € 10,99'}
  ],
  budgets: [
    {name:'Alimentari', color:'#e98972', spent:'€ 472', total:'€ 600', pct:79, left:'€ 128 disponibili'},
    {name:'Tempo libero', color:'#7191bb', spent:'€ 302', total:'€ 400', pct:76, left:'€ 98 disponibili'},
    {name:'Trasporti', color:'#e7ba58', spent:'€ 368', total:'€ 450', pct:82, left:'€ 82 disponibili'}
  ]
};

const renderCategories = () => {
  document.querySelector('#categoryList').innerHTML = demo.categories.map(item => `<li><i style="background:${item.color}"></i><span>${item.name}</span><strong>${item.value} · ${item.percent}</strong></li>`).join('');
};
const renderTransactions = () => {
  document.querySelector('#transactionList').innerHTML = demo.transactions.map(item => `<div class="transaction-item"><div class="transaction-logo" style="background:${item.bg}">${item.icon}</div><div class="transaction-info"><strong>${item.title}</strong><small>${item.category}</small></div><div class="transaction-value"><strong class="${item.incoming?'in':''}">${item.value}</strong><small>${item.date}</small></div></div>`).join('');
};
const renderBudgets = () => {
  document.querySelector('#budgetList').innerHTML = demo.budgets.map(item => `<div class="budget-row"><div class="budget-row-head"><span><i style="background:${item.color}"></i>${item.name}</span><strong>${item.spent} <em>di ${item.total}</em></strong></div><div class="budget-bar"><i style="width:${item.pct}%;background:${item.color}"></i></div><div class="budget-status"><span>${item.pct}% utilizzato</span><span>${item.left}</span></div></div>`).join('');
};

const modal = document.querySelector('#transactionModal');
const toggleModal = open => { modal.classList.toggle('open', open); modal.setAttribute('aria-hidden', String(!open)); if(open) setTimeout(() => modal.querySelector('input[name="amount"]').focus(), 100); };
document.querySelector('#newTransaction').addEventListener('click', () => toggleModal(true));
document.querySelector('.modal-close').addEventListener('click', () => toggleModal(false));
modal.addEventListener('click', event => { if(event.target === modal) toggleModal(false); });
document.addEventListener('keydown', event => { if(event.key === 'Escape') toggleModal(false); });
document.querySelector('#transactionForm').addEventListener('submit', event => {
  event.preventDefault(); toggleModal(false); event.currentTarget.reset();
  const toast = document.querySelector('.toast'); toast.classList.add('show'); setTimeout(() => toast.classList.remove('show'), 2800);
});
document.querySelector('.mobile-menu').addEventListener('click', () => document.querySelector('.sidebar').classList.toggle('open'));
document.querySelectorAll('.nav-item').forEach(item => item.addEventListener('click', () => { document.querySelectorAll('.nav-item').forEach(link => link.classList.remove('active')); item.classList.add('active'); document.querySelector('.sidebar').classList.remove('open'); }));

renderCategories(); renderTransactions(); renderBudgets();

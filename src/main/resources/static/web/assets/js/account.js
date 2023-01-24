let id = new URLSearchParams(window.location.search).get("id");

const app = Vue.createApp({
  data() {
    return {
      account: [],
      transactions: [],
      accountId: [],
      anio: ""
    };
  },

  created() {
    this.anio = new Date().getFullYear();

    axios
      .get("/api/clients/current")
      .then((response) => {
        this.account = response.data.accounts.sort(function (a, b) { return a.id - b.id });
        this.accountId = this.account.find(acc => acc.id == id);
        this.transactions = this.accountId.transaction;
        console.log(this.accountId);
      })
  },

  methods: {
    logOut() {
      Swal.fire({
        title: 'Are you sure?',
        text: "Are you sure you want to log out?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Log Out'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.post("/api/logout")
            .then(response => location.href = "./reveal/index.html")
            .catch(function (error) {
              alert(error)
            })
        }
      })
    },
  },
}).mount("#app");
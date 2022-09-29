const app = Vue.createApp({
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
      accountType: "",
      accountId: ""
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.clients = response.data;
        this.accounts = this.clients.accounts;
        this.accounts = this.accounts.filter(state => state.active == true);

        this.loans = response.data.clientLoans;
      })
      .catch(function (error) {
        console.log(error);
      });
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
            .catch(function (error){
              alert(error)
            })
        }
      })
    },

    createAccount() {
      axios
        .post("/api/clients/current/accounts", `accountType=${this.accountType}`)
        .then((response) =>
          Swal.fire({
            icon: "success",
            title: "Great!",
            text: "Your account have been created",
            timer: 1500,
          }),
          setTimeout(() => { location.href = "/web/accounts.html"}, 2000)
        )
        .catch((response) =>
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "You have more than 3 accounts",
          })
        );
    },

    removeAccount(){
      axios.patch('/api/clients/current/accounts/remove',`accountId=${this.accountId}`)
      .then(() => {
          Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: "your account was deleted",
              showConfirmButton: false,
              timer:1500
          }),
          setTimeout(() => { location.href = "/web/accounts.html"}, 1500)
      })
      .catch((error) => {
          Swal.fire({
              icon: 'error',
              showConfirmButton: true,
              text: error.response.data
          })
      });
  }
  },
}).mount("#app");

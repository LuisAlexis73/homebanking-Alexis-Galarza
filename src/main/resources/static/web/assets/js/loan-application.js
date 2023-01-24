const app = Vue.createApp({
  data() {
    return {
      client: [],
      accounts: [],
      payments: [],
      loans: [],
      clientLoans: [],
      payment: "",
      name: "",
      loanId: "",
      loanPays: "",
      amount: null,
      accountDestiny: "",
      anio: ""
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.allData(response);
        this.clientLoans = response.data.clientLoans;
        console.log(this.clientLoans);

        this.anio = new Date().getFullYear();
      })
      .catch((error) => {
        console.log(error);
      });

    axios
      .get("/api/loans")
      .then((response) => {
        this.getLoans(response);
        console.log(this.getLoans);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
    allData(response) {
      this.client = response.data;
      this.accounts = this.client.accounts;
      this.loans = response.data.clientLoans;
    },

    getLoans(response) {
      this.loans = response.data;
    },

    newDate(creationDate) {
      return new Date(creationDate).toLocaleDateString("es-AR", {
        month: "2-digit",
        year: "2-digit",
      });
    },

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

    loanPetition() {
      axios
        .post("/api/loans", {
          id: `${this.loanId}`,
          amount: `${this.amount}`,
          payments: `${this.loanPays}`,
          accountDestiny: `${this.accountDestiny}`,
        })
        .then(
          () =>
            Swal.fire({
              icon: "success",
              title: "Great!",
              text: "the loan was requested successfully",
              timer: 1500,
            }),
          setTimeout(() => { location.href = "/web/accounts.html" }, 1500)
        )
        .catch((error) => {
          Swal.fire({
            icon: 'error',
            showConfirmButton: true,
            text: `${error.response.data}`
          })
        });
    },
  },

  computed: {
    interestPerPayments() {
      switch (this.loans) {

      }
    }
  }
}).mount("#app");

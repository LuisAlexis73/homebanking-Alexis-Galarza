const app = Vue.createApp({
  data() {
    return {
      allClients: [],
      cards: [],
      accounts: [],
      accountsId: [],

      transferAccount: [],

      selectAccountOrigin: "",
      selectAccountDestiny: "",
      selectAmount: "",
      selectDescription: "",

      someoneSelectAccountOrigin: "",
      someoneSelectAccountDestiny: "",
      someoneSelectAmount: "",
      someoneSelectDescription: "",
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.cards = response.data.cards;
        this.accounts = response.data.accounts;
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

    transactionOwnAccount() {
      axios.post(
        "/api/transactions",
        `amount=${this.selectAmount}&description=${this.selectDescription}&accountOrigin=${this.selectAccountOrigin}&accountDestiny=${this.selectAccountDestiny}`,
        {headers:{'content-type': 'application/x-www-form-urlencoded'}}
      )
        .then((respone) =>
          Swal.fire({
            position: "top-end",
            icon: "success",
            title: "Transaction done!",
            showConfirmButton: false,
            timer: 2500,
          })
        )
        .then(
          (response) =>
            (window.location.href = `./account.html?id=${this.MatchIdOwnAccount}`)
        )
        .catch((error) =>
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: error.response.data,
          })
        );
    },

    transactionSomeoneAccount() {
      axios
        .post(
          "/api/transactions",
          `amount=${this.someoneSelectAmount}&description=${this.someoneSelectDescription}&accountOrigin=${this.someoneSelectAccountOrigin}&accountDestiny=${this.someoneSelectAccountDestiny}`
        )
        .then((respone) =>
          Swal.fire({
            position: "top-end",
            icon: "success",
            title: "Transaction done!",
            showConfirmButton: false,
            timer: 1500,
          })
        )
        .then(
          (response) =>
            (window.location.href = `./account.html?id=${this.MatchIdThirdAccount}`)
        )
        .then((response) => alert("transcation done!"))
        .catch((error) =>
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: `${error.response.data}`,
          })
        );
    },
  },

  computed: {
    filterNameMatchIdOwnAccount() {
      this.MatchIdOwnAccount = this.accounts
        .filter((response) => response.number == this.selectAccountOrigin)
        .map((response) => response.id);
      console.log(this.MatchIdOwnAccount);
    },
    filterNameMatchIdThirdAccount() {
      this.MatchIdThirdAccount = this.accounts
        .filter((response) => response.number == this.someoneSelectAccountOrigin)
        .map((response) => response.id);
      console.log(this.MatchIdThirdAccount);
    },
  },
}).mount("#app");

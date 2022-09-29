const app = Vue.createApp({
  data() {
    return {
      cards: [],
      selectedColor: null,
      options: [
        { text: "SILVER", value: "SILVER" },
        { text: "TITANIUM", value: "TITANIUM" },
        { text: "GOLD", value: "GOLD" },
      ],
      selectedType: null,
      options: [
        { value: "DEBIT", text: "DEBIT" },
        { text: "CREDIT", value: "CREDIT" },
      ],
    };
  },

  created() {
    axios.get("/api/clients/current").then((response) => {
      this.cards = response.data.cards;
      console.log(this.cards);
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
    
    createCards() {
      axios
        .post(
          "/api/clients/current/cards",
          `cardType=${this.selectedType}&cardColor=${this.selectedColor}`
        )
        .then((response) =>
        Swal.fire({
          icon: "success",
          title: "Great!",
          text: "Your card have been created",
          timer: 2500,
        }),
        setTimeout(() => { location.href = "/web/cards.html"}, 2000)
        )
        .catch((x) => 
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: "Some was wrong.. plesea check all fields."
        }),
        setTimeout(() => { location.href = "/web/create-cards.html"}, 2500)
        )
    },
  },
}).mount("#app");

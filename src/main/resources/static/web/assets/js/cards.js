const app = Vue.

  createApp({
    data() {
      return {
        cards: [],
        cardId: "",
        anio: ""
      };
    },

    created() {
      axios
        .get("/api/clients/current")
        .then((response) => {
          this.cards = response.data.cards;
          console.log(this.cards);
        })
        .catch(function (error) {
          console.log(error);
        });

      this.anio = new Date().getFullYear();
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

      removeCard() {
        axios
          .patch('/api/clients/current/cards/remove', `idCard=${this.cardId}`)
          .then(() => {
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: "your card was deleted",
              showConfirmButton: false,
              timer: 1500
            }),
              setTimeout(() => { location.href = "/web/cards.html" }, 1500)
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
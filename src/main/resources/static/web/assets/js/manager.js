const { createApp } = Vue;

createApp({
  data() {
    return {
      clients: [],
      firstName: "",
      lastName: "",
      email: "",
      anio: ""
    };
  },

  created() {

    this.loadData();
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

    addClient() {
      axios
        .post("/rest/clients", {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
        })
        .then(() => this.addClient())
        .catch(function (error) {
          console.log(error);
        });
    },

    loadData() {
      axios
        .get("/api/clients")
        .then((response) => {
          this.clients = response.data;
          console.log(this.clients);

          this.anio = new Date().getFullYear();
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  },
}).mount("#app");

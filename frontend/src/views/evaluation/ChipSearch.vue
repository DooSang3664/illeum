<template>
  <v-card class="mx-auto" max-width="300">
    <v-toolbar flat color="transparent">
      <v-toolbar-title>빠른검색</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon @click="$refs.search.focus()">
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
    </v-toolbar>

    <v-container class="py-0">
      <v-row align="center" justify="start">
        <v-col v-for="(selection, i) in selections" :key="selection.text" class="shrink">
          <v-chip :disabled="loading" close @click:close="selected.splice(i, 1)">
            <v-icon left v-text="selection.icon"></v-icon>
            {{ selection.text }}
          </v-chip>
        </v-col>

        <v-col v-if="!allSelected" cols="12">
          <v-text-field ref="search" v-model="search" full-width hide-details label="Search" single-line></v-text-field>
        </v-col>
      </v-row>
    </v-container>

    <v-divider v-if="!allSelected"></v-divider>

    <v-list>
      <template v-for="item in categories">
        <v-list-item v-if="!selected.includes(item)" :key="item.text" :disabled="loading" @click="showSelected(item)">
          <v-list-item-avatar>
            <v-icon :disabled="loading" v-text="item.icon"></v-icon>
          </v-list-item-avatar>
          <v-list-item-title v-text="item.text"></v-list-item-title>
        </v-list-item>
      </template>
    </v-list>

    <v-divider></v-divider>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    items: [
      {
        text: '지각',
        icon: 'mdi-nature',
      },
      {
        text: '결석',
        icon: 'mdi-glass-wine',
      },
      {
        text: '출석1등',
        icon: 'mdi-calendar-range',
      },
      {
        text: '채팅참여1등',
        icon: 'mdi-bike',
      },
    ],
    loading: false,
    search: '',
    selected: [],
  }),

  created() {
    console.log('attend', this.attend);
  },
  computed: {
    allSelected() {
      return this.selected.length === this.items.length;
    },
    categories() {
      const search = this.search.toLowerCase();

      if (!search) return this.items;

      return this.items.filter((item) => {
        const text = item.text.toLowerCase();

        return text.indexOf(search) > -1;
      });
    },
    selections() {
      const selections = [];

      for (const selection of this.selected) {
        selections.push(selection);
      }

      return selections;
    },
  },

  watch: {
    selected() {
      this.search = '';
    },
  },

  methods: {
    next() {
      this.loading = true;

      setTimeout(() => {
        this.search = '';
        this.selected = [];
        this.loading = false;
      }, 2000);
    },
    showSelected(item) {
      this.selected.push(item);
      this.$emit('selected', this.selected);
    },
  },
};
</script>

<style scoped></style>

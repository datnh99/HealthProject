<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <h4 class="card-title">Class Management</h4>
          </template>
          <div class="table-responsive text-left">
            <a-spin :spinning="loading">
              <a-icon
                type="loading"
                slot="indicator"
                style="font-size: 24px"
                spin
              />
              <a-table
                :data-source="data"
                :columns="columns"
                :pagination="false"
              >
                <div
                  slot="filterDropdown"
                  slot-scope="{
                    setSelectedKeys,
                    selectedKeys,
                    confirm,
                    clearFilters,
                    column,
                  }"
                  style="padding: 8px"
                >
                  <a-input
                    v-ant-ref="(c) => (searchInput = c)"
                    :placeholder="`Search ${column.dataIndex}`"
                    :value="selectedKeys[0]"
                    style="width: 188px; margin-bottom: 8px; display: block;"
                    @change="
                      (e) =>
                        setSelectedKeys(e.target.value ? [e.target.value] : [])
                    "
                    @pressEnter="
                      () => handleSearch(selectedKeys, column.dataIndex)
                    "
                  />
                  <a-button
                    type="primary"
                    icon="search"
                    size="small"
                    style="width: 90px; margin-right: 8px"
                    @click="() => handleSearch(selectedKeys, column.dataIndex)"
                  >
                    Search
                  </a-button>
                  <a-button
                    size="small"
                    style="width: 90px"
                    @click="() => handleReset(column.dataIndex, clearFilters)"
                  >
                    Reset
                  </a-button>
                </div>
                <a-icon
                  slot="filterIcon"
                  slot-scope="filtered"
                  type="search"
                  :style="{ color: filtered ? '#108ee9' : undefined }"
                />
                <template
                  slot="customRender"
                  slot-scope="text, record, index, column"
                >
                  <span
                    v-if="searchText && searchedColumn === column.dataIndex"
                  >
                    <template
                      v-for="(fragment, i) in text
                        .toString()
                        .split(
                          new RegExp(
                            `(?<=${searchText})|(?=${searchText})`,
                            'i'
                          )
                        )"
                    >
                      <mark
                        v-if="
                          fragment.toLowerCase() === searchText.toLowerCase()
                        "
                        :key="i"
                        class="highlight"
                        >{{ fragment }}</mark
                      >
                      <template v-else>{{ fragment }}</template>
                    </template>
                  </span>
                  <template v-else>
                    {{ text }}
                  </template>
                </template>
              </a-table>
              <div class="gutter-example pt-md pagnigation-custom">
                <a-pagination
                  v-model="current"
                  show-quick-jumper
                  :default-current="1"
                  :total="totals"
                  @change="paginate"
                />
              </div>
            </a-spin>
          </div>
        </card>
      </div>
    </div>
  </div>
</template>
<script>
import ClassRepository from "../api/class.js";

export default {
  data() {
    return {
      data: [],
      current: 1,
      totals: 0,
      loading: false,
      formDataSearch: {
        className: "",
        teacherName: "",
      },
      searchText: "",
      searchInput: null,
      searchedColumn: "",
      columns: [
        {
          title: "Class Name",
          dataIndex: "className",
          key: "className",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.name
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              }, 0);
            }
          },
        },
        {
          title: "Teacher Account",
          dataIndex: "teacherAccount",
          key: "teacherAccount",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.address
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Teacher Name",
          dataIndex: "teacherName",
          key: "teacherName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.address
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
      ],
    };
  },
  created() {
    this.searchClass();
  },
  methods: {
    paginate(current = 1) {
      this.loading = true;
      this.current = current;
      ClassRepository.searchClass(this.formDataSearch, this.current).then(
        (res) => {
          this.data = res.data.data.items;
          this.totals = res.data.data.total;
          this.loading = false;
        }
      );
    },
    searchClass() {
      this.loading = true;
      ClassRepository.searchClass(this.formDataSearch, 1).then((res) => {
        this.data = res.data.data.items;
        this.totals = res.data.data.total;
        this.current = 1;
        this.loading = false;
      });
    },
    handleSearch(selectedKeys, dataIndex) {
      this.searchText = selectedKeys[0];
      this.searchedColumn = dataIndex;
      if (dataIndex === "className") {
        this.formDataSearch.className = selectedKeys[0];
      } else if (dataIndex === "teacherName") {
        this.formDataSearch.teacherName = selectedKeys[0];
      }
      this.searchClass();
    },

    handleReset(dataIndex, clearFilters) {
      if (dataIndex === "className") {
        this.formDataSearch.className = "";
      } else if (dataIndex === "teacherName") {
        this.formDataSearch.teacherName = "";
      }
      clearFilters();
      this.searchClass();
      this.searchText = "";
    },
  },
};
</script>
<style scoped>
.highlight {
  background-color: rgb(255, 192, 105);
  padding: 0px;
}

.gutter-example {
  padding-bottom: 40px;
}

.pagnigation-custom {
  float: right;
}
</style>

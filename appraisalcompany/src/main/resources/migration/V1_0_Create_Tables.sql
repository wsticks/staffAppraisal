/*create Role table */
CREATE TABLE role (
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  name varchar(128)  NOT NULL,
  description varchar(525)  NOT NULL,
  status varchar(16)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  is_hidden varchar(16)  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE(unique_key)
);

/*create Permission table */
CREATE TABLE permission(
  id bigint IDENTITY(1, 1)  NOT NULL,
  name varchar(128)  NOT NULL,
  description varchar(525)  NOT NULL,
  code varchar(256)  NOT NULL,
  status varchar(16)  NULL,
  is_hidden varchar(16)  NOT NULL,
  PRIMARY KEY (id)
);

/*create User table */

CREATE TABLE "user"(
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  first_name varchar(255)  NULL,
  last_name varchar(255)  NULL,
  email varchar(255)  NULL,
  "password" varchar(255)  NULL,
  phone varchar(16)  NULL,
  "address" text  NULL,
  role_id VARCHAR(32)  NOT NULL REFERENCES  role (unique_key),
  status varchar(16)  NOT NULL,
  last_login_date varchar(50)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  PRIMARY KEY (unique_key)
);

/*create Token table */
CREATE TABLE token(
  id bigint IDENTITY(1, 1)  NOT NULL,
  "user" varchar(32)  NULL REFERENCES "user" (unique_key),
  token varchar(64)  NULL,
  status varchar(16)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  expires_at datetime NULL,
);

/*create Role permission table */
CREATE TABLE role_permission(
  id bigint IDENTITY(1, 1)  NOT NULL,
  role_id bigint  NOT NULL REFERENCES role (id),
  permission_id bigint  NOT NULL REFERENCES permission (id),
  status varchar(16)  NOT NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
);

/*create notification template table */
CREATE TABLE notification_template(
  id bigint IDENTITY(1, 1)  NOT NULL,
  action varchar(32)  NOT NULL,
  channel varchar(32)  NOT NULL,
  template varchar(255)  NOT NULL,
  fields varchar(max)  NOT NULL,
  subject varchar(255)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  is_active tinyint  NOT NULL
);

/*create Recruitment table */
CREATE TABLE staff(
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  surname TEXT NULL,
  first_name TEXT NULL,
  middle_name TEXT  NULL,
  email TEXT  NOT NULL,
  sex varchar(255)  NULL,
  age varchar(255)  NOT NULL,
  marital_status varchar(255)  NULL,
  employee_number varchar(255)  NOT NULL,
  name_of_school TEXT  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  level varchar(255)  NULL,
  "status" varchar(255)  NULL,
  department varchar(255)  NULL,
  entitled_leave_days VARCHAR(255) null,
  salary varchar(255)  NULL,
    PRIMARY KEY (id),
  UNIQUE(unique_key)
);

/*create Department table */
CREATE TABLE department (
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  name varchar(128)  NOT NULL,
  description varchar(525)  NOT NULL,
  status varchar(16)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  PRIMARY KEY (id),
  UNIQUE(unique_key)
);

/*create Loan table */
CREATE TABLE loan(
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  employee varchar(255) NULL,
  purpose TEXT NULL,
  amount DECIMAL  NULL,
  repayment_period varchar(255)  NOT NULL,
  payment_method varchar(255)  NULL,
  payee_name varchar(255)  NOT NULL,
  account_number varchar(255)  NULL,
  bank_sort_code varchar(255)  NOT NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  "status" varchar(255)  NULL,
  PRIMARY KEY (id),
  UNIQUE(unique_key)
);

/*create Leave table */
CREATE TABLE leave(
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  employee varchar(255) NULL,
  number_of_days varchar(255) NULL,
  start_date datetime  NULL,
  end_date datetime  NOT NULL,
  leave_type varchar(255)  NULL,
  pending_leave_days varchar(255)  NOT NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  "status" varchar(255)  NULL,
  PRIMARY KEY (id),
  UNIQUE(unique_key)
);
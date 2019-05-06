# JavaFinalProject

## 指定題：飯店訂房系統

### TABLE 解釋

1. RESV TABLE：用來儲存房間的預訂資料  

columns 為：  
r_type         房間種類 "ONE_ADULT", "TWO_ADULTS", "FOUR_ADULTS"  
hotel_name     飯店名稱  
r_index        房間編號  
in_date        入住日期  
out_date       退房日期  
uid            訂房者的ID  
id             訂單編號  

2. HOTEL TABLE：用來儲存飯店的資料

columns 為：  
hotel_name     飯店名稱  
star           星級  
one_adult      一人房數量  
two_adults     二人房數量  
four_adults    四人房數量  
price          價格  

3. ORDERS TABLE：用來儲存訂單資料

columns 為：
hotel_name     飯店名稱  
one_adult      一人房數量  
two_adults     二人房數量  
four_adults    四人房數量  
in_date        入住日期  
out_date       退房日期  
uid            訂房者的ID  
id             訂單編號
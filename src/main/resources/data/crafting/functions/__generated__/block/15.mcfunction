#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s[tag=pos] daisringtimer 20
execute if score @s[tag=pos] daisringtimer matches 3610.. run scoreboard players set @s daisringtimer 1
scoreboard players add @s[tag=up] daisringtimer 30
execute if score @s[tag=up] daisringtimer matches 3610.. run scoreboard players set @s daisringtimer 1
scoreboard players remove @s[tag=neg] daisringtimer 20
execute if score @s[tag=neg] daisringtimer matches ..0 run scoreboard players set @s daisringtimer 3600
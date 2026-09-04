#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s musictimer -= Systick time
execute if score @s musictimer matches ..0 run function music:sorcerer/nextpart
execute if score @s music.died matches 1.. run function music:sorcerer/end
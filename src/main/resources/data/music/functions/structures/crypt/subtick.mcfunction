#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s musictimer -= Systick time
execute if score @s musictimer matches ..0 run function music:structures/crypt/play
execute as @s[predicate=!music:structures/crypt/in_crypt] run function music:structures/crypt/stop
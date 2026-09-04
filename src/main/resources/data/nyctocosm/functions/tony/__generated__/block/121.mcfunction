#built using mc-build (https://github.com/mc-build/mc-build)

particle dust 0 0 0 2 ~ ~ ~ 0.6 0.6 0.6 0 500 force
data merge entity @s {Invulnerable:1b,CustomNameVisible:1b,Sitting:1b,variant:"minecraft:black",CollarColor:15b,Tags:["tony","tonycat"],CustomName:'[{"text":"VES","color":"black","italic":false,"obfuscated":true},{"text":"Tony","color":"light_purple","italic":false,"obfuscated":false},{"text":"PER","color":"black","obfuscated":true}]'}
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s timer = temp0 rng
scoreboard players operation @s timer /= #100000 rng
scoreboard players operation @s timer %= #200 rng
scoreboard players add @s timer 200
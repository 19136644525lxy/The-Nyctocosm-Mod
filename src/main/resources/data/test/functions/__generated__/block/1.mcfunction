#built using mc-build (https://github.com/mc-build/mc-build)

execute as Flubberschnub run function magic:calcstatsdamage
execute as Flubberschnub run function test:__generated__/block/2
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as Flubberschnub run function damage:damage_dream/apply
scoreboard players operation @s m.damage_taken.dream += damageAmt value
execute as @s if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function test:__generated__/block/3
scoreboard players reset IsPlayer value
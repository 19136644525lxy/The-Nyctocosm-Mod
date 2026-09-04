#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set damageAmt value 4000
tag @s add damager
execute as @s run function items:scythe/__generated__/block/25
effect give @s speed 1 1 true
tag @s add damagetemp
execute at @s run function items:scythe/raycast
tag @s remove damagetemp
tag @e remove scythehit
execute at @s run playsound nyctocosm:items.scythe.dash master @a[distance=..20] ~ ~ ~ 4
tag @s remove enddash
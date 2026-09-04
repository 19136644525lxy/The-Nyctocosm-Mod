#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove doomswinghit
scoreboard players set damageAmt value 19000
tag @e[tag=damagetemp,limit=1] add damager
execute as @s run function items:semblanceofdoom/__generated__/block/65
data merge entity @s {Fire:100s}
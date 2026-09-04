#built using mc-build (https://github.com/mc-build/mc-build)

item modify entity @s weapon.mainhand items:semblanceofdoom/doomswing
execute as @s[nbt={OnGround:1b}] at @s positioned ~ ~1.5 ~ run function items:semblanceofdoom/__generated__/block/62
execute as @s[nbt={OnGround:0b}] at @s positioned ~ ~0.9 ~ run function items:semblanceofdoom/__generated__/block/63
playsound nyctocosm:items.semblanceofdoom.swing master @a[distance=..30] ~ ~ ~
tag @s add damagetemp
execute as @e[tag=doomswinghit] at @s run function items:semblanceofdoom/__generated__/block/64
tag @s remove damagetemp
scoreboard players set @s doomswingcooldown 30
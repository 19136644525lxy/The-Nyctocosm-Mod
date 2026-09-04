#built using mc-build (https://github.com/mc-build/mc-build)

item modify entity @s weapon.offhand items:semblanceofdoom/doomswing
playsound nyctocosm:items.semblanceofdoom.shoot master @a[distance=..30] ~ ~ ~
execute at @s anchored eyes positioned ^ ^ ^1 run function items:semblanceofdoom/__generated__/block/71
scoreboard players set @s doomcastcooldown 30
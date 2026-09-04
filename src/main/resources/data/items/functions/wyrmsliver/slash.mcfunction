#built using mc-build (https://github.com/mc-build/mc-build)

effect give @s slowness 1 1 true
item modify entity @s weapon.mainhand items:wyrmsliver/sliverslash
execute at @s run playsound nyctocosm:items.wyrmsliver.slash master @a[distance=..20] ~ ~ ~ 4
execute as @s[nbt={OnGround:0b}] run summon marker ~ ~1.625 ~ {Tags:["wyrmsliverslash","justsummoned"]}
execute as @s[nbt={OnGround:1b}] run summon marker ~ ~1.625 ~ {Tags:["wyrmsliverslash","justsummoned","grounded"]}
data modify entity @e[tag=justsummoned,tag=wyrmsliverslash,limit=1] data.UUID set from entity @s UUID
execute at @s positioned ~ ~1.625 ~ as @e[tag=justsummoned] run function items:wyrmsliver/__generated__/block/38
execute at @s positioned ~ ~1 ~ positioned ^ ^ ^1 run particle flash ~ ~ ~ 0 0 0 0 1 force
execute positioned ~ ~1.625 ~ positioned ^ ^ ^1.5 run particle minecraft:sweep_attack ~ ~ ~ 0 0 0 0 1
scoreboard players set @s slashcooldown 20
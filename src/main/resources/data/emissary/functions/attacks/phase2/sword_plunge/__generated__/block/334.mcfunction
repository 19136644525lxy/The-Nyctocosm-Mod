#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ~ ~-10 ~ rotated ~ 0 run function effects:shockwave/meteor_shower
execute positioned ~ ~ ~ rotated ~ 0 run function effects:shockwave/meteor_shower
execute positioned ~ ~10 ~ rotated ~ 0 run function effects:shockwave/meteor_shower
execute positioned ~ ~20 ~ rotated ~ 0 run function effects:shockwave/meteor_shower
execute positioned ~ ~30 ~ rotated ~ 0 run function effects:shockwave/meteor_shower
execute positioned ~ ~40 ~ rotated ~ 0 run function effects:shockwave/meteor_shower
execute positioned ~ ~-10 ~ run particle flash ~ ~ ~ 0.2 0.2 0.2 0 4 force
execute positioned ~ ~ ~ run particle flash ~ ~ ~ 0.2 0.2 0.2 0 4 force
execute positioned ~ ~10 ~ run particle flash ~ ~ ~ 0.2 0.2 0.2 0 4 force
execute positioned ~ ~20 ~ run particle flash ~ ~ ~ 0.2 0.2 0.2 0 4 force
execute positioned ~ ~30 ~ run particle flash ~ ~ ~ 0.2 0.2 0.2 0 4 force
execute positioned ~ ~40 ~ run particle flash ~ ~ ~ 0.2 0.2 0.2 0 4 force
summon falling_block ~ ~10 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~10 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~10 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~10 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~10 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~20 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~20 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~20 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~20 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~20 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~30 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~30 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~30 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~30 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~30 ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~ ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~ ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~ ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~ ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
summon falling_block ~ ~ ~ {BlockState:{Name:"minecraft:fire"},Time:1,DropItem:0b,HurtEntities:0b,Tags:["justsummoned"]}
execute as @e[tag=justsummoned] at @s run function emissary:attacks/phase2/sword_plunge/__generated__/block/335
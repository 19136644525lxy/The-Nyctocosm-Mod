#built using mc-build (https://github.com/mc-build/mc-build)

summon item_display ~ ~ ~ {item_display:"head",Tags:["attack","emissary","sword_plunge","projectile"],brightness:{sky:10,block:0},transformation:{left_rotation:[0f,0f,0f,1f],right_rotation:[0f,0f,0f,1f],translation:[0f,0f,0f],scale:[10f,10f,10f]},item:{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:12}}}
execute positioned over world_surface run playsound nyctocosm:emissary.attack.sword_plunge.fall master @a[distance=..300] ~ ~ ~ 20
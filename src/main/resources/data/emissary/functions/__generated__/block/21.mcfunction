#built using mc-build (https://github.com/mc-build/mc-build)

forceload add ~ ~ ~ ~
execute in nyctocosm:emissary_domain run forceload add ~ ~ ~ ~
summon marker ~ ~ ~ {Tags:["subspace","emissaryentrance"]}
execute as @a[distance=..2] run function emissary:__generated__/block/22
execute in nyctocosm:emissary_domain positioned over world_surface run function emissary:__generated__/block/23
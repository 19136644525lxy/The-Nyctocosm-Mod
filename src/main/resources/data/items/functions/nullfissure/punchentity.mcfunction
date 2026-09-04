#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s as @e[type=#nyctocosm:livingthings,sort=nearest,limit=1,nbt={HurtTime:10s}] run function items:nullfissure/__generated__/block/45
advancement revoke @s only items:detection/hit_with_nullfissure